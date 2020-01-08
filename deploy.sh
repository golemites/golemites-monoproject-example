#!/usr/bin/env sh

# Full circle roundtrip with a local golemites checkout
./gradlew assemble install deploy --rerun-tasks --include-build ../golemites