package screens.menu

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.assets.loadImage
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.*
import pl.karol202.uranium.webcanvas.component.ui.button
import pl.karol202.uranium.webcanvas.component.ui.stack
import pl.karol202.uranium.webcanvas.values.*
import ui.menuBottomRight
import ui.menuButtons
import ui.menuCenter

fun WCRenderScope.menuScreen(key: Any = AutoKey,
                             size: Vector,
                             onGameStart: () -> Unit) =
		group(key = key) {
			+ background(size = size)
			+ menuCenter(size = size) {
				+ title()
				+ menuButtons(buttons = listOf("Start" to onGameStart))
			}
			+ menuBottomRight(key = "bottom_right",
			                  size = size) {
				+ authorLabel()
			}
		}

private fun WCRenderScope.background(key: Any = AutoKey,
                                     size: Vector) =
		image(key = key,
		      image = loadImage("menu_background.png"),
		      drawBounds = Bounds(size = size))

private fun WCRenderScope.title(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Arkanoid",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)

private fun WCRenderScope.authorLabel(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(x = -20.0, y = -10.0),
		         text = "by Karol Jurski",
		         font = Font.create(15, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.END,
		         baseline = TextBaseline.BOTTOM)
