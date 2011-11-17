package sbtrelease

import sbt._

object Mercurial extends VCS {
  import Utilities._

  def getExec = {
    val maybeOsName = sys.props.get("os.name").map(_.toLowerCase)
    val maybeIsWindows = maybeOsName.filter(_.contains("windows"))
    maybeIsWindows.map(_ => "hg.exe").getOrElse("hg")
  }


  def getVCSIdentifierName = ".hg"

  def VCSName = "Mercurial"

  private def cmd(args: Any*): ProcessBuilder = Process(getExec +: args.map(_.toString))

  def remoteRepo = (cmd("paths", "default") !!) trim

  def currentHash = (cmd("id", "-i") !!).trim

  def currentBranch = (cmd("branch") !!) trim

  def add(files: String*) = cmd(("add" +: files): _*)

  def commit(message: String) = cmd("commit", "-m", message)

  def tag(name: String) = cmd("tag", "-m", "Releasing " + name, name)

  def pushTags = cmd("push")

  def status = cmd("status")

  def isTracked(file: String) = cmd("status", "--all", "-I", file)

  def isModified(file: String) = (cmd("status", "--all", "-I", file) !!).trim.startsWith("M ")

  def pushCurrentBranch = {
    cmd("push", remoteRepo)
  }

  def resetHard(hash: String) = cmd("update", "-C")
}
