package com.vers.webcrawler.config

import cats.effect.{ConcurrentEffect, Resource}
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContextExecutorService
import scala.concurrent.duration.Duration

class ClientConfig

final case class ServerConfig(host: String, port: Int, threadPoolSize: Int)


object ClientConfig {
  def httpClient[F[_] : ConcurrentEffect](futurePool: ExecutionContextExecutorService): Resource[F, Client[F]] =
    BlazeClientBuilder[F](futurePool)
      .withMaxWaitQueueLimit(maxWaitQueueLimit = 10000)
      .withIdleTimeout(Duration.Inf)
      .resource
}