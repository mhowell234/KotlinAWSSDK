plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "me.mhowell"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("aws.sdk.kotlin:s3:0.19.5-beta")

}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest("1.7.21")

            dependencies {
                implementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
            }
        }
    }
}

application {
    mainClass.set("v3.AppKt")
}
