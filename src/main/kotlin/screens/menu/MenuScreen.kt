package screens.menu

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.primitives.*
import pl.karol202.uranium.webcanvas.component.ui.button
import pl.karol202.uranium.webcanvas.component.ui.stack
import pl.karol202.uranium.webcanvas.values.*

private const val BUTTON_OFFSET_Y = 100.0
private val BUTTON_SIZE = Vector(250.0, 70.0)
private val BUTTON_SHAPE_SVG = "M 0 0 L 0 46.248047 L 23.751953 70 L 250 70 L 250 24.160156 L 225.83984 0" +
		"L 0 0 z M 4 4 L 221.83984 4 L 246 26.160156 L 246 66 L 27.751953 66 L 4 42.248047 L 4 4 z"

fun WCRenderScope.menuScreen(key: Any = AutoKey,
                             size: Vector,
                             onGameStart: () -> Unit) =
		group(key = key) {
			+ background(size = size)
			+ center(size = size) {
				+ title()
				+ menuButtonsPanel {
					+ stack(elements = listOf("Start" to onGameStart),
					        elementOffset = Vector(y = BUTTON_OFFSET_Y)) { (text, onClick), _ ->
						+ menuButton(text = text,
						             onClick = onClick)
					}
				}
			}
		}

private fun WCRenderScope.background(key: Any = AutoKey,
                                     size: Vector) =
		rectFill(key = key,
		         bounds = Bounds(size = size),
		         fillStyle = Color.raw("black"))

private fun WCRenderScope.center(key: Any = AutoKey,
                                 size: Vector,
                                 content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = (size / 2.0)!!,
		          content = content)

private fun WCRenderScope.title(key: Any = AutoKey) =
		textFill(key = key,
		         position = Vector(y = -100.0),
		         text = "Arkanoid",
		         font = Font.create(72, "monospace"),
		         fillStyle = Color.raw("white"),
		         align = TextAlign.CENTER)

private fun WCRenderScope.menuButtonsPanel(key: Any = AutoKey,
                                           content: WCRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(x = -BUTTON_SIZE.x / 2),
		          content = content)

private fun WCRenderScope.menuButton(key: Any = AutoKey,
                                     text: String,
                                     onClick: () -> Unit) =
		button(key = key,
		       size = BUTTON_SIZE,
		       idleContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.37),
			                           text = text)
		       },
		       hoverContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.55),
			                           text = text)
		       },
		       clickContent = {
			       + menuButtonContent(color = Color.hsl(235, 1.0, 0.7),
			                           text = text)
		       },
		       onClick = onClick)

private fun WCRenderScope.menuButtonContent(key: Any = AutoKey,
                                            color: Color,
                                            text: String) =
		group(key = key) {
			+ pathFill(path = Path.fromSVG(BUTTON_SHAPE_SVG),
			           fillStyle = color)
			+ textFill(key = "text",
			           position = Vector(x = BUTTON_SIZE.x / 2, y = BUTTON_SIZE.y / 2),
			           text = text,
			           font = Font.create(30, "monospace"),
			           fillStyle = color,
			           align = TextAlign.CENTER,
			           baseline = TextBaseline.MIDDLE)
		}
