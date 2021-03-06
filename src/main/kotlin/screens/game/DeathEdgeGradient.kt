package screens.game

import pl.karol202.uranium.arkade.htmlcanvas.ArkadeRenderScope
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.arkade.htmlcanvas.component.primitives.rectFill
import pl.karol202.uranium.arkade.htmlcanvas.values.Bounds
import pl.karol202.uranium.arkade.htmlcanvas.values.Color
import pl.karol202.uranium.arkade.htmlcanvas.values.Gradient
import pl.karol202.uranium.arkade.htmlcanvas.values.Vector

const val DEATH_EDGE_GRADIENT_HEIGHT = 20.0
const val DEATH_EDGE_GRADIENT_OPACITY = 0.5

fun ArkadeRenderScope.deathEdgeGradient(key: Any = AutoKey,
                                        width: Double,
                                        bottomY: Double) =
		rectFill(key = key,
		         bounds = Bounds(0.0, bottomY - DEATH_EDGE_GRADIENT_HEIGHT, width, DEATH_EDGE_GRADIENT_HEIGHT),
		         fillStyle = Gradient.Linear.simple(start = Vector(0.0, bottomY - DEATH_EDGE_GRADIENT_HEIGHT),
		                                            end = Vector(0.0, bottomY),
		                                            startColor = Color.rgba(255, 0, 0, 0.0),
		                                            endColor = Color.rgba(255, 0, 0, DEATH_EDGE_GRADIENT_OPACITY)))
