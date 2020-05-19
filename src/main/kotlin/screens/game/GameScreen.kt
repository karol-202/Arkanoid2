package screens.game

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.scale
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.event.eventHandler
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.canvasFill
import pl.karol202.uranium.webcanvas.component.primitives.circleFill
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.CircleCollider
import pl.karol202.uranium.webcanvas.physics.collider.Collider
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.InputEvent
import pl.karol202.uranium.webcanvas.values.Vector

private const val SCREEN_COLLIDER_WIDTH = 0.2

private val PADDLE_SIZE = Vector(100.0, 20.0)
private const val PADDLE_BASELINE_BOTTOM_Y = 50.0

private const val BALL_RADIUS = 10.0
private const val BALL_INITIAL_SPEED = 700.0

class GameScreen(props: Props) : WCAbstractComponent<GameScreen.Props>(props),
                                 UStateful<GameScreen.State>
{
	data class Props(override val key: Any,
	                 val size: Vector) : UProps

	sealed class State : UState
	{
		data class Prepare(override val paddleX: Double) : State()
		{
			override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

			fun play(ballState: WCRigidbody.State) = Play(paddleX, ballState)
		}

		data class Play(override val paddleX: Double,
		                val ballState: WCRigidbody.State) : State()
		{
			override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
		}

		abstract val paddleX: Double

		abstract fun withPaddleX(paddleX: Double): State
	}

	private val paddleBaselineY get() = props.size.y - PADDLE_BASELINE_BOTTOM_Y

	override var state by state(createInitialState())

	private fun createInitialState(): State = State.Prepare(props.size.x / 2.0)

	override fun WCRenderBuilder.render()
	{
		val state = state

		+ canvasFill(color = Color.raw("black"))
		+ when(state)
		{
			is State.Prepare -> statePrepare()
			is State.Play -> statePlay(state)
		}
	}

	private fun WCRenderScope.statePrepare() =
			group {
				+ startTrigger()
				+ paddleMovement {
					+ paddle()
					+ translate(vector = Vector(y = -BALL_RADIUS)) {
						+ ball()
					}
				}
			}

	private fun WCRenderScope.startTrigger() =
			eventHandler(allListener = ::handleTriggerEvent)

	private fun WCRenderScope.statePlay(state: State.Play) =
			collisionDomain {
				+ screenCollider()
				+ paddleMovement {
					+ paddle()
				}
				+ ballMovement(state.ballState) {
					+ ball()
				}
			}

	private fun WCRenderScope.screenCollider() =
			scale(vector = props.size) {
				val width = SCREEN_COLLIDER_WIDTH
				+ collider(key = 1, collider = RectCollider(Bounds(-width, -width, width, 1.0 + (width * 2))))
				+ collider(key = 2, collider = RectCollider(Bounds(1.0, -width, width, 1.0 + (width * 2))))
				+ collider(key = 3, collider = RectCollider(Bounds(0.0, -width, 1.0, width)))
				+ collider(key = 4, collider = RectCollider(Bounds(0.0, 1.0, 1.0, width)))
			}

	private fun WCRenderScope.paddleMovement(content: WCRenderBuilder.() -> Unit) =
			translate(key = key,
			          vector = Vector(y = paddleBaselineY)) {
				+ mouseFollower(currentX = state.paddleX,
				                onMove = ::setPaddleX) {
					+ content.render()
				}
			}

	private fun WCRenderScope.paddle() =
			group {
				val bounds = Bounds(start = Vector(x = -PADDLE_SIZE.x / 2), size = PADDLE_SIZE)
				+ collider(collider = RectCollider(bounds))
				+ rectFill(bounds = bounds,
				           color = Color.raw("white"))
			}

	private fun WCRenderScope.ballMovement(ballState: WCRigidbody.State,
	                                       content: WCRenderBuilder.() -> Unit) =
			rigidbody(mass = 1.0,
			          state = ballState,
			          onStateChange = ::setBallState,
			          collider = CircleCollider(radius = BALL_RADIUS),
			          onCollision = { bounce(it.selfNormal, 1.0) }) {
				+ content.render()
			}

	private fun WCRenderScope.ball() =
			circleFill(center = Vector.ZERO,
			           radius = BALL_RADIUS,
			           color = Color.raw("white"))

	private fun setPaddleX(paddleX: Double) = setState { withPaddleX(paddleX) }

	private fun handleTriggerEvent(event: InputEvent) = when
	{
		event is InputEvent.Key -> startGame()
		event is InputEvent.Mouse && event.type == InputEvent.Mouse.Type.UP -> startGame()
		else -> Unit
	}

	private fun startGame() = setState { (this as? State.Prepare)?.play(createBallState(paddleX)) ?: this }

	private fun createBallState(paddleX: Double) =
			WCRigidbody.State(position = Vector(paddleX, paddleBaselineY - BALL_RADIUS),
			                  velocity = Vector.randomDirection() * BALL_INITIAL_SPEED)

	private fun setBallState(ballState: WCRigidbody.State) =
			setState { (this as? State.Play)?.copy(ballState = ballState) ?: this }
}

fun WCRenderScope.gameScreen(key: Any = AutoKey,
                             size: Vector) = component(::GameScreen, GameScreen.Props(key, size))
