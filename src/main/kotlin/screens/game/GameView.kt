package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.flip
import pl.karol202.uranium.arkade.htmlcanvas.component.physics.Rigidbody
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

fun ArkadeRenderScope.gameView(key: Any = AutoKey,
                               size: Vector,
                               state: GameState,
                               onPaddlePositionChange: (Double) -> Unit,
                               onBallStateChange: (Rigidbody.State) -> Unit,
                               onBrickHit: (String) -> Unit,
                               onDeath: () -> Unit,
                               onStart: () -> Unit,
                               onStartAgain: () -> Unit,
                               onTryAgain: () -> Unit) =
		flip(key = key,
		     size = size,
		     vertical = state.level.isVerticallyInverted) {
			+ deathEdgeGradient(key = "death_edge",
			                    width = size.x,
			                    bottomY = size.y)
			+ stateView(size = size,
			            state = state,
			            onPaddlePositionChange = onPaddlePositionChange,
			            onBallStateChange = onBallStateChange,
			            onBrickHit = onBrickHit,
			            onDeath = onDeath,
			            onStart = onStart,
			            onStartAgain = onStartAgain,
			            onTryAgain = onTryAgain)
		}

private fun ArkadeRenderScope.stateView(size: Vector,
                                        state: GameState,
                                        onPaddlePositionChange: (Double) -> Unit,
                                        onBallStateChange: (Rigidbody.State) -> Unit,
                                        onBrickHit: (String) -> Unit,
                                        onDeath: () -> Unit,
                                        onStart: () -> Unit,
                                        onStartAgain: () -> Unit,
                                        onTryAgain: () -> Unit) = when(state)
{
	is GameState.Prepare -> prepareStateComponent(size = size,
	                                              gameState = state,
	                                              onPaddlePositionChange = onPaddlePositionChange,
	                                              onStart = onStart)
	is GameState.Play -> playStateComponent(size = size,
	                                        gameState = state,
	                                        onPaddlePositionChange = onPaddlePositionChange,
	                                        onBallStateChange = onBallStateChange,
	                                        onBrickHit = onBrickHit,
	                                        onDeath = onDeath)
	is GameState.Win -> winComponent(size = size,
	                                 gameState = state,
	                                 onStartAgain = onStartAgain)
	is GameState.GameOver -> gameOverComponent(size = size,
	                                           gameState = state,
	                                           onTryAgain = onTryAgain)
}
