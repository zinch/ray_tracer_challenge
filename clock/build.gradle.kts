plugins {
    id("raytracer.java-application-conventions")
}

dependencies {
    implementation(project(":core"))
}

application {
    // Define the main class for the application.
    mainClass.set("raytracer.Clock")
}