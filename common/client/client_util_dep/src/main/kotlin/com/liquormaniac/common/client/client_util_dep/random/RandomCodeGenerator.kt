package com.liquormaniac.common.client.client_util_dep.random

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomCodeGenerator
fun verificationCodeGenerator(email: String) : String
{
    val random: Random = Random(email.hashCode())
    val codeInt: Int = random.nextInt(999999)

    return codeInt.toString().padStart(6, '0')
}