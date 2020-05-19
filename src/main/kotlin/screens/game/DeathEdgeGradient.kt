package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Gradient
import pl.karol202.uranium.webcanvas.values.Vector

const val DEATH_EDGE_GRADIENT_HEIGHT = 20.0
const val DEATH_EDGE_GRADIENT_OPACITY = 0.5

fun WCRenderScope.deathEdgeGradient(width: Double,
                                    bottomY: Double) =
		rectFill(bounds = Bounds(0.0, bottomY - DEATH_EDGE_GRADIENT_HEIGHT, width, DEATH_EDGE_GRADIENT_HEIGHT),
		         fillStyle = Gradient.Linear.simple(start = Vector(0.0, bottomY - DEATH_EDGE_GRADIENT_HEIGHT),
		                                            end = Vector(0.0, bottomY),
		                                            startColor = Color.rgba(255, 0, 0, 0.0),
		                                            endColor = Color.rgba(255, 0, 0, DEATH_EDGE_GRADIENT_OPACITY)))
