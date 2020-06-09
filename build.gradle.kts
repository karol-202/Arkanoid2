plugins {
    kotlin("js") version "1.3.72"
}

repositories {
    jcenter()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("pl.karol202.uranium:uranium-arkade-htmlcanvas-js:0.2")
}

kotlin.target.browser { }
