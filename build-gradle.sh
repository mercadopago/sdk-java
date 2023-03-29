#!/bin/sh
VERSION="7.6.1"
BREW_VERSION=${VERSION:0:1}
INSTALL_DIR=gradle
CMD=$(which gradle)

check_gradle_installed() {
  echo "Checking if gradle is installed globally..."
  which gradle > /dev/null 2>&1
  return $?
}

install() {
  which brew > /dev/null 2>&1
  if [ $? -eq 0 ];
  then
    echo "Homebrew detected. Install gradle globally?"
    echo "[yes/no] default: yes"
    read brew
    if [[ "$brew" == "yes" ]] || [[ "$brew" == "" ]];
    then
      echo "brew"
      install_brew
    else
      echo "local"
      install_locally
    fi
  else
    install_locally
  fi
}

install_brew() {
  echo "Installing version $BREW_VERSION globally with Homebrew..."
  brew install "gradle@$BREW_VERSION"
  ln -s /usr/local/opt/gradle@$BREW_VERSION/bin/gradle /usr/local/bin/gradle
  CMD=$(which gradle)
}

install_locally() {
  echo "Installing version $VERSION locally @ ./$INSTALL_DIR/..."
  ls gradle > /dev/null 2>&1
  if [ $? -gt 0 ];
  then
    mkdir gradle
  fi
  curl "https://downloads.gradle-dn.com/distributions/gradle-$VERSION-bin.zip" > "gradle/gradle-$VERSION-bin.zip"
  unzip -d gradle "gradle/gradle-$GRADLE_VERSION-bin.zip"

  export GRADLE_HOME="gradle/gradle-$VERSION"

  CMD="$GRADLE_HOME/bin/gradle"
}

init() {
  ls settings.gradle > /dev/null 2>&1

  if [ $? -gt 0 ];
  then
    echo "Setting up project..."
    $CMD init <<< "\r\rno\r"
  fi
}

build() {
  which gradle > /dev/null 2>&1

  if [ $? -gt 0 ];
  then
    export GRADLE_HOME="gradle/gradle-$VERSION"
    CMD="$GRADLE_HOME/bin/gradle"
  fi

  $CMD build
}

check_gradle_installed

installed=$?
if [ $installed -gt 0 ] ;
then 
  echo "Gradle not found. Installing..."
  install
fi

init
build
