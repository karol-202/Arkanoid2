package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.*
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.rectFill
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.textFill
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Color
import pl.karol202.uranium.arkade.htmlcanvas.values.Font
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

private const val HP_BOX_OFFSET_X = 50.0

fun ArkadeRenderScope.topBar(key: Any = AutoKey,
                             bounds: Bounds,
                             gameState: GameState) =
		translate(key = key,
		          vector = bounds.start) {
			+ background(size = bounds.size)

			+ levelLabel(key = "level_label",
			             levelName = gameState.level.name)

			+ flip(size = Vector(x = bounds.size.x),
			       horizontal = true) {
				+ hpBoxes(height = bounds.size.y,
				          hp = gameState.playerHp)
			}
		}

private fun ArkadeRenderScope.background(key: Any = AutoKey,
                                         size: Vector) =
		rectFill(key = key,
		         bounds = Bounds(size = size),
		         fillStyle = Color.hsl(0, 0.0, 0.4))

private fun ArkadeRenderScope.levelLabel(key: Any = AutoKey,
                                         levelName: String) =
		textFill(key = key,
		         position = Vector(30.0, 30.0),
		         text = levelName,
		         font = Font.create(30, "monospace").bold(),
		         fillStyle = Color.raw("white"))

private fun ArkadeRenderScope.hpBoxes(key: Any = AutoKey,
                                      height: Double,
                                      hp: Int) =
		scale(key = key,
		      vector = Vector(x = 1.0, y = height)) {
			repeat(hp) { boxIndex ->
				+ translate(key = boxIndex,
				            vector = Vector(x = HP_BOX_OFFSET_X * boxIndex)) {
					+ rectFill(bounds = Bounds(5.0, 0.15, 40.0, 0.7),
					           fillStyle = Color.raw("red"))
				}
			}
		}
