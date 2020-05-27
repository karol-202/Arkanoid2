package screens.game

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.scale
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

const val TOP_BAR_HEIGHT = 40.0
private const val HP_BOX_SPACE_WIDTH = 50.0
private const val HP_BOX_SPACE_HEIGHT = 40.0
private val HP_BOX_BOUNDS = Bounds(0.15, 0.15, 0.7, 0.7)

fun WCRenderScope.topBar(key: Any = AutoKey,
                         screenSize: Vector,
                         gameState: GameState) =
		group(key = key) {
			+ rectFill(bounds = Bounds(size = Vector(screenSize.x, TOP_BAR_HEIGHT)),
			           fillStyle = Color.hsl(0, 0.0, 0.4))
			+ translate(vector = Vector(screenSize.x)) {
				+ scale(vector = Vector(x = -HP_BOX_SPACE_WIDTH,
				                        y = HP_BOX_SPACE_HEIGHT)) {
					repeat(gameState.playerHp) { boxIndex ->
						+ translate(key = boxIndex,
						            vector = Vector(x = boxIndex.toDouble())) {
							+ rectFill(key = boxIndex,
							           bounds = HP_BOX_BOUNDS,
							           fillStyle = Color.raw("red"))
						}
					}
				}
			}
		}
