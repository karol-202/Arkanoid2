package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.primitives.canvasFill
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

class GameScreen(props: Props) : WCAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameState>
{
	data class Props(override val key: Any,
	                 val size: Vector) : UProps

	override var state by state(createInitialState())

	private val gameViewSize get() = props.size - Vector(y = TOP_BAR_HEIGHT)

	private fun createInitialState() = GameState.initial(size = gameViewSize,
	                                                     level = Level.Level3)

	override fun WCRenderBuilder.render()
	{
		+ canvasFill(key = "background",
		             fillStyle = Color.raw("black"))
		+ translate(vector = Vector(y = TOP_BAR_HEIGHT)) {
			+ when(val state = state)
			{
				is GameState.Prepare -> prepareStateComponent(size = gameViewSize,
				                                              gameState = state,
				                                              onPaddlePositionChange = ::setPaddleX,
				                                              onStart = ::startGame)
				is GameState.Play -> playStateComponent(size = gameViewSize,
				                                        gameState = state,
				                                        onPaddlePositionChange = ::setPaddleX,
				                                        onBallStateChange = ::setBallState,
				                                        onBrickHit = ::hitBrick,
				                                        onDeath = ::decreasePlayerHp)
				is GameState.Win -> winComponent(size = gameViewSize,
				                                 gameState = state,
				                                 onStartAgain = ::startAgain)
				is GameState.GameOver -> gameOverComponent(size = gameViewSize,
				                                           gameState = state,
				                                           onTryAgain = ::tryAgain)
			}
		}
		+ topBar(screenSize = props.size,
		         gameState = state)
		+ deathEdgeGradient(key = "death_edge",
		                    width = props.size.x,
		                    bottomY = props.size.y)
	}

	private fun setPaddleX(paddleX: Double) = setState { withPaddleX(paddleX) }

	private fun startGame() = setStateIf<GameState.Prepare> {
		play(ballState = createBallState(gameViewSize, paddleX))
	}

	private fun setBallState(ballState: WCRigidbody.State) = setStateIf<GameState.Play> {
		withBallState(ballState)
	}

	private fun hitBrick(brickId: String) = setStateIf<GameState.Play> {
		withBrickHit(brickId)
	}

	private fun decreasePlayerHp() = setStateIf<GameState.Play> {
		withPlayerHpDecremented()
	}

	private fun startAgain() = setStateIf<GameState.Win> {
		createInitialState()
	}

	private fun tryAgain() = setStateIf<GameState.GameOver> {
		createInitialState()
	}

	private inline fun <reified S : GameState> setStateIf(crossinline builder: S.() -> GameState) =
			setState { if(this is S) builder() else this }
}

fun WCRenderScope.gameScreen(key: Any = AutoKey,
                             size: Vector) = component(::GameScreen, GameScreen.Props(key, size))
