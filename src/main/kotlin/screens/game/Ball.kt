package screens.game

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.render.render
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.assets.loadImage
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.circleFill
import pl.karol202.uranium.webcanvas.component.primitives.image
import pl.karol202.uranium.webcanvas.physics.Collision
import pl.karol202.uranium.webcanvas.physics.collider.CircleCollider
import pl.karol202.uranium.webcanvas.values.*
import kotlin.math.PI

const val BALL_RADIUS = 10.0
const val BALL_SPEED = 700.0
const val BALL_INITIAL_ANGLE_MIN = 5.0 / 4.0 * PI
const val BALL_INITIAL_ANGLE_MAX = 7.0 / 4.0 * PI

fun WCRenderScope.ballMovement(key: Any = AutoKey,
                               ballState: WCRigidbody.State,
                               onBallStateChange: (WCRigidbody.State) -> Unit,
                               onBrickHit: (String) -> Unit,
                               onDeathEdgeReach: () -> Unit,
                               content: WCRenderBuilder.() -> Unit) =
		rigidbody(key = key,
		          mass = 1.0,
		          state = ballState,
		          onStateChange = onBallStateChange,
		          collider = CircleCollider(circle = Circle(radius = BALL_RADIUS)),
		          onCollision = { onCollision(it, onBrickHit, onDeathEdgeReach) }) {
			+ content.render()
		}

fun WCRenderScope.ball(key: Any = AutoKey) =
		image(key = key,
		      image = loadImage("ball.png"),
		      drawBounds = Bounds(-BALL_RADIUS, -BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2))

private fun WCRigidbody.State.onCollision(collision: Collision,
                                          onBrickHit: (String) -> Unit,
                                          onDeathEdgeReach: () -> Unit) = when(val payload = collision.otherCollider.payload)
{
	is ColliderType.Paddle -> {
		val newBallDirection = (collision.selfCollider.boundingBox.center - collision.otherCollider.boundingBox.center).normalized
		copy(velocity = newBallDirection * BALL_SPEED)
	}
	is ColliderType.Brick -> {
		onBrickHit(payload.id)
		bounce(collision.otherNormal, 1.0)
	}
	is ColliderType.ScreenEdge -> bounce(collision.otherNormal, 1.0)
	is ColliderType.DeathEdge -> {
		onDeathEdgeReach()
		this
	}
	else -> this
}

fun createBallState(screenSize: Vector,
                    paddleX: Double) =
		WCRigidbody.State(position = Vector(paddleX, screenSize.y - PADDLE_BASELINE_BOTTOM_Y - BALL_RADIUS),
		                  velocity = createInitialDirection() * BALL_SPEED)

private fun createInitialDirection() = PolarVector.randomDirection(startAngle = BALL_INITIAL_ANGLE_MIN,
                                                                   endAngle = BALL_INITIAL_ANGLE_MAX).asCartesian
