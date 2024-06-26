plugins {
    id(Conventions.kotlin_module)
}

dependencies {
    implementation(project(":common"))
    implementation(project(":user-api:domain"))

    testImplementation(testFixtures(project(":user-api:domain")))

    testFixturesImplementation(TestingLibs.kotest_arrow)
}
