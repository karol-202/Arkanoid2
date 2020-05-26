package screens.game

import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.circleFill
import pl.karol202.uranium.webcanvas.physics.Collision
import pl.karol202.uranium.webcanvas.physics.collider.CircleCollider
import pl.karol202.uranium.webcanvas.values.Circle
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.PolarVector
import pl.karol202.uranium.webcanvas.values.Vector
import kotlin.math.PI

const val BALL_RADIUS = 10.0
const val BALL_INITIAL_SPEED = 700.0
const val BALL_INITIAL_ANGLE_MIN = 5.0 / 4.0 * PI
const val BALL_INITIAL_ANGLE_MAX = 7.0 / 4.0 * PI

fun WCRenderScope.ballMovement(ballState: WCRigidbody.State,
                               onBallStateChange: (WCRigidbody.State) -> Unit,
                               onBrickHit: (String) -> Unit,
                               onDeathEdgeReach: () -> Unit,
                               content: WCRenderBuilder.() -> Unit) =
		rigidbody(mass = 1.0,
		          state = ballState,
		          onStateChange = onBallStateChange,
		          collider = CircleCollider(circle = Circle(radius = BALL_RADIUS)),
		          onCollision = {
			          onCollision(it, onBrickHit, onDeathEdgeReach)
			          bounce(it.selfNormal, 1.0)
		          }) {
			+ content.render()
		}

fun WCRenderScope.ball() =
		circleFill(center = Vector.ZERO,
		           radius = BALL_RADIUS,
		           fillStyle = Color.raw("white"))

private fun onCollision(collision: Collision,
                        onBrickHit: (String) -> Unit,
                        onDeathEdgeReach: () -> Unit) = when(val payload = collision.otherCollider.payload)
{
	is ColliderType.Brick -> onBrickHit(payload.id)
	is ColliderType.DeathEdge -> onDeathEdgeReach()
	else -> Unit
}

fun createBallState(screenSize: Vector,
                    paddleX: Double) =
		WCRigidbody.State(position = Vector(paddleX, screenSize.y - PADDLE_BASELINE_BOTTOM_Y - BALL_RADIUS),
		                  velocity = createInitialDirection() * BALL_INITIAL_SPEED)

private fun createInitialDirection() = PolarVector.randomDirection(startAngle = BALL_INITIAL_ANGLE_MIN,
                                                                   endAngle = BALL_INITIAL_ANGLE_MAX).asCartesian
