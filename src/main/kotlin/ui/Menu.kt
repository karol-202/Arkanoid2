package ui

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderBuilder
import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.containers.translate
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.rectFill
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Color
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

fun ArkadeRenderScope.menuBackground(key: Any = AutoKey,
                                     size: Vector) =
		rectFill(key = key,
		         bounds = Bounds(size = size),
		         fillStyle = Color.hsla(0, 0.0, 0.0, 0.6))

fun ArkadeRenderScope.menuCenter(key: Any = AutoKey,
                                 size: Vector,
                                 content: ArkadeRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = (size / 2.0)!!,
		          content = content)

fun ArkadeRenderScope.menuBottomRight(key: Any = AutoKey,
                                      size: Vector,
                                      content: ArkadeRenderBuilder.() -> Unit) =
		translate(key = key,
		          vector = Vector(x = size.x, y = size.y),
		          content = content)
