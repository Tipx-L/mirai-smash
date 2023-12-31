plugins {
	val kotlinVersion = "1.8.10"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.serialization") version kotlinVersion
	id("net.mamoe.mirai-console") version "2.16.0"
}

group = "io.github.tipx_l.miraismash"

repositories {
	maven("https://maven.aliyun.com/repository/public")
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
}

mirai {
	jvmTarget = JavaVersion.VERSION_1_8
}
