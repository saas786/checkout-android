#!/bin/bash
set -e

PATH=$PATH:/opt/gocd/build

source /opt/gocd/build/java/buildlib.sh

configure gradle

source /opt/gocd/build/java/test.sh
