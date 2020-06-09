package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.assets.Image
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.Rigidbody
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.rigidbody
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.imageDraw
import pl.karol202.uranium.arkade.htmlcanvas.physics.Collision
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.CircleCollider
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Circle
import pl.karol202.uranium.arkade.htmlcanvas.values.PolarVector
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.render.render
import kotlin.math.PI

const val BALL_RADIUS = 10.0
const val BALL_SPEED = 700.0
const val BALL_INITIAL_ANGLE_MIN = 5.0 / 4.0 * PI
const val BALL_INITIAL_ANGLE_MAX = 7.0 / 4.0 * PI

fun ArkadeRenderScope.ballMovement(key: Any = AutoKey,
                               ballState: Rigidbody.State,
                               onBallStateChange: (Rigidbody.State) -> Unit,
                               onBrickHit: (String) -> Unit,
                               onDeathEdgeReach: () -> Unit,
                               content: ArkadeRenderBuilder.() -> Unit) =
		rigidbody(key = key,
		          mass = 1.0,
		          state = ballState,
		          onStateChange = onBallStateChange,
		          collider = CircleCollider(circle = Circle(radius = BALL_RADIUS)),
		          onCollision = { onCollision(it, onBrickHit, onDeathEdgeReach) }) {
			+ content.render()
		}

fun ArkadeRenderScope.ball(key: Any = AutoKey) =
		imageDraw(key = key,
		          image = Image.load("ball.png"),
		          drawBounds = Bounds(-BALL_RADIUS, -BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2))

private fun Rigidbody.State.onCollision(collision: Collision,
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
		Rigidbody.State(position = Vector(paddleX, screenSize.y - PADDLE_BASELINE_BOTTOM_Y - BALL_RADIUS),
		                  velocity = createInitialDirection() * BALL_SPEED)

private fun createInitialDirection() = PolarVector.randomDirection(startAngle = BALL_INITIAL_ANGLE_MIN,
                                                                   endAngle = BALL_INITIAL_ANGLE_MAX).asCartesian
