package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.flip
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.primitives.canvasFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

const val TOP_BAR_HEIGHT = 40.0

class GameScreen(props: Props) : WCAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameState>
{
	data class Props(override val key: Any,
	                 val size: Vector) : UProps

	override var state by state(createInitialState())

	private val gameViewSize get() = props.size - Vector(y = TOP_BAR_HEIGHT)

	private fun createInitialState() = GameState.initial(size = gameViewSize,
	                                                     level = Level.Level1)

	override fun WCRenderBuilder.render()
	{
		+ canvasFill(fillStyle = Color.raw("black"))
		+ translate(vector = Vector(y = TOP_BAR_HEIGHT)) {
			+ gameView(size = gameViewSize,
			           state = state,
			           onPaddlePositionChange = ::setPaddleX,
					   onBallStateChange = ::setBallState,
					   onBrickHit = ::hitBrick,
					   onDeath = ::decreasePlayerHp,
					   onStart = ::startGame,
					   onStartAgain = ::startAgain,
					   onTryAgain = ::tryAgain)
		}
		+ topBar(key = "topbar",
		         bounds = Bounds(size = Vector(x = props.size.x, y = TOP_BAR_HEIGHT)),
		         gameState = state)
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
