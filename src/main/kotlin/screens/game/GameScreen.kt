package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.flip
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.webcanvas.component.primitives.canvasFill
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

private val PADDLE_BASELINE = 50.0
private val PADDLE_SIZE = Vector(100.0, 20.0)

private const val INITIAL_BALL_SPEED = 100.0

class GameScreen(props: Props) : WCAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameScreen.State>
{
	data class Props(override val key: Any,
	                 val size: Vector) : UProps

	sealed class State : UState
	{
		data class Waiting(override val paddleX: Double) : State()
		{
			override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
		}

		data class Playing(override val paddleX: Double,
		                   val ballState: WCRigidbody.State) : State()
		{
			override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
		}

		abstract val paddleX: Double

		abstract fun withPaddleX(paddleX: Double): State
	}

	override var state by state(createInitialState())

	private fun createInitialState(): State = State.Waiting(props.size.x / 2.0)

	override fun WCRenderBuilder.render()
	{
		+ canvasFill(color = Color.raw("black"))
		+ collisionDomain {
			+ bottom {
				+ paddle()
			}
		}
	}

	private fun WCRenderScope.bottom(builder: WCRenderBuilder.() -> Unit) = translate(vector = Vector(y = props.size.y)) {
		+ flip(vertical = true) { + builder.render() }
	}

	private fun WCRenderScope.paddle() = translate(vector = Vector(y = PADDLE_BASELINE)) {
		+ mouseFollower(currentX = state.paddleX,
		                onMove = ::setPaddleX) {
			+ rectFill(bounds = Bounds(start = (-PADDLE_SIZE / 2.0)!!, size = PADDLE_SIZE),
			           color = Color.raw("white"))
		}
	}

	private fun setPaddleX(paddleX: Double) = setState { withPaddleX(paddleX) }
}

fun WCRenderScope.gameScreen(key: Any = AutoKey,
                             size: Vector) = component(::GameScreen, GameScreen.Props(key, size))
