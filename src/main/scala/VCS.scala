package sbtrelease

import sbt._

trait VCS {

import Utilities._

	def getVCSIdentifierName: String

	def VCSName: String

  def getExec: String

  private def cmd(args: Any*): ProcessBuilder = Process(getExec +: args.map(_.toString))

  def currentHash: String

  def currentBranch: ProcessBuilder

  def add(files: String*): ProcessBuilder

  def commit(message: String):ProcessBuilder

  def tag(name: String): ProcessBuilder

  def pushTags: ProcessBuilder

  def status: ProcessBuilder

  def isTracked(file: String) : ProcessBuilder

  def isModified(file: String) : Boolean

  def pushCurrentBranch:ProcessBuilder

  def resetHard(hash: String): ProcessBuilder
}