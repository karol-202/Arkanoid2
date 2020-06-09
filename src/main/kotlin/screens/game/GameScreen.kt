package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.component.base.ArkadeAbstractComponent
import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.translate
import pl.karol202.uranium.arkade.htmlcanvas.component.event.eventHandler
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.Rigidbody
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.viewportFill
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Color
import pl.karol202.uranium.arkade.htmlcanvas.values.InputEvent
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

const val TOP_BAR_HEIGHT = 40.0

class GameScreen(props: Props) : ArkadeAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameScreen.State>
{
	data class Props(override val key: Any,
	                 val size: Vector,
	                 val onQuit: () -> Unit) : UProps

	data class State(val gameState: GameState,
	                 val pause: Boolean) : UState

	override var state by state(State(gameState = createInitialGameState(),
	                                  pause = false))

	private val gameViewSize get() = props.size - Vector(y = TOP_BAR_HEIGHT)

	private fun createInitialGameState() = GameState.initial(size = gameViewSize,
	                                                         level = Level.Level1)

	override fun ArkadeRenderBuilder.render()
	{
		+ viewportFill(fillStyle = Color.raw("black"))
		+ translate(vector = Vector(y = TOP_BAR_HEIGHT)) {
			+ gameView(size = gameViewSize,
			           state = state.gameState,
			           onPaddlePositionChange = ::setPaddleX,
					   onBallStateChange = ::setBallState,
					   onBrickHit = ::hitBrick,
					   onDeath = ::decreasePlayerHp,
					   onStart = ::startGame,
					   onStartAgain = ::startAgain,
					   onTryAgain = ::tryAgain)
			if(state.pause) + gameMenuView(size = gameViewSize,
			                               onBackToGame = ::disablePause,
			                               onQuit = props.onQuit)
		}
		+ topBar(key = "topbar",
		         bounds = Bounds(size = Vector(x = props.size.x, y = TOP_BAR_HEIGHT)),
		         gameState = state.gameState)
		+ pauseTrigger(onPauseToggle = ::togglePause)
	}

	private fun ArkadeRenderScope.pauseTrigger(key: Any = AutoKey,
	                                       onPauseToggle: () -> Unit) =
			eventHandler(key = key,
			             keyListener = { if(it.type == InputEvent.Key.Type.UP && it.key == "Escape") onPauseToggle() })

	private fun setPaddleX(paddleX: Double) = setGameState { withPaddleX(paddleX) }

	private fun startGame() = setGameStateIf<GameState.Prepare> {
		play(ballState = createBallState(gameViewSize, paddleX))
	}

	private fun setBallState(ballState: Rigidbody.State) = setGameStateIf<GameState.Play> {
		withBallState(ballState)
	}

	private fun hitBrick(brickId: String) = setGameStateIf<GameState.Play> {
		withBrickHit(brickId)
	}

	private fun decreasePlayerHp() = setGameStateIf<GameState.Play> {
		withPlayerHpDecremented()
	}

	private fun startAgain() = setGameStateIf<GameState.Win> {
		createInitialGameState()
	}

	private fun tryAgain() = setGameStateIf<GameState.GameOver> {
		createInitialGameState()
	}

	private fun togglePause()
	{
		if(!state.gameState.isGameEnded) setState { copy(pause = !pause) }
	}

	private fun disablePause() =
			setState { copy(pause = false) }

	private inline fun <reified S : GameState> setGameStateIf(crossinline builder: S.() -> GameState) =
			setState { if(!pause && gameState is S) copy(gameState = gameState.builder()) else this }

	private inline fun setGameState(crossinline builder: GameState.() -> GameState) =
			setState { if(!pause) copy(gameState = gameState.builder()) else this }
}

fun ArkadeRenderScope.gameScreen(key: Any = AutoKey,
                             size: Vector,
                             onQuit: () -> Unit) = component(::GameScreen, GameScreen.Props(key, size, onQuit))
