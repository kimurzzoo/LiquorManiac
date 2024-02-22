plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "LiquorManiac"
include("batchscheduler")
include("bff")
include("chat")
include("gpt")
include("liquor")
include("user")
include("common")
include("common:client", "common:client:client_util_dep")
include("common:core", "common:core:core_web", "common:core:core_liquor")
include("common:domain", "common:domain:domain_user", "common:domain:domain_core", "common:domain:domain_liquor")
include("common:others", "common:others:redis_config")
