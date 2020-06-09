package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.scale
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.Rigidbody
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.collider.colliderProvider
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.arkade.htmlcanvas.physics.collider.RectCollider
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

private const val SCREEN_COLLIDER_WIDTH = 0.2

fun ArkadeRenderScope.playStateComponent(size: Vector,
                                         gameState: GameState.Play,
                                         onPaddlePositionChange: (Double) -> Unit,
                                         onBallStateChange: (Rigidbody.State) -> Unit,
                                         onBrickHit: (String) -> Unit,
                                         onDeath: () -> Unit) =
		collisionDomain {
			+ screenCollider(size = size)
			+ gameState.bricks.map {
				brick(it)
			}
			+ paddleMovement(screenSize = size,
			                 positionX = gameState.paddleX,
			                 onPositionChange = onPaddlePositionChange) {
				+ paddle()
			}
			+ ballMovement(ballState = gameState.ballState,
			               onBallStateChange = onBallStateChange,
			               onBrickHit = onBrickHit,
			               onDeathEdgeReach = onDeath) {
				+ ball()
			}
		}

private fun ArkadeRenderScope.screenCollider(size: Vector) =
		scale(key = "screen_collider",
		      vector = size) {
			val width = SCREEN_COLLIDER_WIDTH
			+ colliderProvider(key = 1,
			                   collider = RectCollider(Bounds(-width, -width, width, 1.0 + (width * 2)),
			                                   payload = ColliderType.ScreenEdge))
			+ colliderProvider(key = 2,
			                   collider = RectCollider(Bounds(1.0, -width, width, 1.0 + (width * 2)),
			                                   payload = ColliderType.ScreenEdge))
			+ colliderProvider(key = 3,
			                   collider = RectCollider(Bounds(0.0, -width, 1.0, width),
			                                   payload = ColliderType.ScreenEdge))
			+ colliderProvider(key = 4,
			                   collider = RectCollider(Bounds(0.0, 1.0, 1.0, width),
			                                   payload = ColliderType.DeathEdge))
		}
