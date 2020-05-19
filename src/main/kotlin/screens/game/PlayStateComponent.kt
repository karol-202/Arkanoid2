package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.scale
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Vector

private const val SCREEN_COLLIDER_WIDTH = 0.2

fun WCRenderScope.playStateComponent(size: Vector,
                                     gameState: GameState.Play,
                                     onPaddlePositionChange: (Double) -> Unit,
                                     onBallStateChange: (WCRigidbody.State) -> Unit,
                                     onDeath: () -> Unit) =
		collisionDomain {
			+ screenCollider(size = size)
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = onPaddlePositionChange) {
				+ paddle()
			}
			+ ballMovement(ballState = gameState.ballState,
			               onBallStateChange = onBallStateChange,
			               onDeathEdgeReach = onDeath) {
				+ ball()
			}
		}

private fun WCRenderScope.screenCollider(size: Vector) =
		scale(vector = size) {
			val width = SCREEN_COLLIDER_WIDTH
			+ collider(key = 1, collider = RectCollider(Bounds(-width, -width, width, 1.0 + (width * 2))))
			+ collider(key = 2, collider = RectCollider(Bounds(1.0, -width, width, 1.0 + (width * 2))))
			+ collider(key = 3, collider = RectCollider(Bounds(0.0, -width, 1.0, width)))
			+ collider(key = 4, collider = RectCollider(Bounds(0.0, 1.0, 1.0, width), payload = ColliderType.DeathEdge))
		}
