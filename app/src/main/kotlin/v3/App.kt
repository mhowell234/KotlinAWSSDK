package v3

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.createBucket
import aws.sdk.kotlin.services.s3.deleteBucket
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.model.BucketLocationConstraint
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.ByteStream
import kotlinx.coroutines.runBlocking
import java.util.UUID

class App {

    val run = runBlocking {
        S3Client.fromEnvironment { region = REGION }
            .use { s3 ->
                createBucket(s3)
                addObject(s3)
                cleanUp(s3)
            }
    }

    private suspend fun createBucket(s3Client: S3Client) {
        println("creating bucket $BUCKET")
        s3Client.createBucket {
            bucket = BUCKET
            createBucketConfiguration {
                locationConstraint = BucketLocationConstraint.fromValue(REGION)
            }
        }
        println("created $BUCKET")
    }

    private suspend fun addObject(s3Client: S3Client) {
        s3Client.putObject {
            bucket = BUCKET
            key = KEY
            body = ByteStream.fromString("Testing")
        }
        println("Created $KEY")
    }

    private suspend fun cleanUp(s3Client: S3Client) {
        println("Cleaning up")
        s3Client.deleteObject {
            bucket = BUCKET
            key = KEY
        }
        println("tossed object")

        s3Client.deleteBucket {
            bucket = BUCKET
        }

        println("tossed bucket")
    }

    companion object {
        private const val REGION = "us-west-2"
        private val BUCKET = "bucket-mhowell-${UUID.randomUUID()}"
        private const val KEY = "key"
    }
}

fun main() {
    println(App().run)
}
