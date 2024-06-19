plugins {

    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

android {
    namespace = "com.example.calculos"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
publishing{
    publications{
        register("MeuMapa",MavenPublication::class){
            groupId = "com.example"
            artifactId = "calculos"
            version = "1.0"
            artifact("$buildDir/outputs/aar/Calculos-debug.aar")
        }
    }
    repositories{
        maven{
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/Joao-lu/Minha_biblioteca")
                credentials{
                    username = project.findProperty("usuario") as String? ?: ""
                    password = project.findProperty("token") as String? ?: ""
                }
        }
    }

}

