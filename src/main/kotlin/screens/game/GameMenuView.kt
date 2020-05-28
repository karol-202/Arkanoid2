package screens.game

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector
import ui.menuButtons

private const val MENU_CONTENT_OFFSET_Y = -100.0

fun WCRenderScope.gameMenuView(key: Any = AutoKey,
                               size: Vector,
                               onBackToGame: () -> Unit,
                               onQuit: () -> Unit) =
		group(key = key) {
			+ background(size = size)
			+ center(size = size) {
				+ menuButtons(buttons = listOf("Powrót do gry" to onBackToGame,
				                               "Menu główne" to onQuit))
			}
		}

private fun WCRenderScope.background(key: Any = AutoKey,
                                     size: Vector) =
		rectFill(key = key,
		         bounds = Bounds(size = size),
		         fillStyle = Color.hsla(0, 0.0, 0.0, 0.6))

private fun WCRenderScope.center(key: Any = AutoKey,
                                 size: Vector,
                                 content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(x = size.x / 2, y = size.y / 2 + MENU_CONTENT_OFFSET_Y),
		          content = content)
