SHELL := /bin/bash

# JAVA_HOME on this machine points at a path that no longer exists; fall back
# to the Homebrew openjdk@17 install so every target works out of the box.
ifeq (,$(wildcard $(JAVA_HOME)/bin/java))
export JAVA_HOME := $(shell brew --prefix openjdk@17 2>/dev/null)/libexec/openjdk.jdk/Contents/Home
endif

export ANDROID_HOME ?= /opt/homebrew/share/android-commandlinetools
ADB := $(ANDROID_HOME)/platform-tools/adb
EMULATOR := $(ANDROID_HOME)/emulator/emulator
AVDMANAGER := $(ANDROID_HOME)/cmdline-tools/latest/bin/avdmanager

AVD_NAME ?= eriantys

.PHONY: help server android-build android-install android-run avd emulator ip devices clean

help:
	@echo "Targets:"
	@echo "  make server           Build (if needed) and run the game server (port 12345, hardcoded)"
	@echo "  make android-build    Assemble the Android debug APK"
	@echo "  make android-install  Install the debug APK on the first connected device/emulator"
	@echo "  make android-run      Build, install, and launch the app on the first connected device/emulator"
	@echo "  make avd              Create a local emulator (once) named '$(AVD_NAME)'"
	@echo "  make emulator         Boot the '$(AVD_NAME)' emulator"
	@echo "  make devices          List adb devices/emulators currently visible"
	@echo "  make ip               Print this machine's LAN IP (enter it as 'Server address' on a physical device)"
	@echo "  make clean            ./gradlew clean"
	@echo ""
	@echo "Emulator connects to the server via 10.0.2.2; a physical phone needs 'make ip' instead."

server:
	./gradlew :desktop:shadowJar -q
	java -jar $$(ls desktop/build/libs/desktop-*-all.jar | head -1) server

android-build:
	./gradlew :android:assembleDebug -q

android-install: android-build
	$(ADB) install -r android/build/outputs/apk/debug/android-debug.apk

android-run: android-install
	$(ADB) shell am start -n it.polimi.ingsw.android/.MainActivity

avd:
	@if $(AVDMANAGER) list avd | grep -q "Name: $(AVD_NAME)$$"; then \
		echo "AVD '$(AVD_NAME)' already exists"; \
	else \
		echo no | $(AVDMANAGER) create avd -n $(AVD_NAME) -k "system-images;android-35;google_apis;arm64-v8a" -d pixel_6; \
	fi

emulator:
	$(EMULATOR) -avd $(AVD_NAME)

devices:
	$(ADB) devices -l

ip:
	@ipconfig getifaddr en0 2>/dev/null || ipconfig getifaddr en1 2>/dev/null || echo "No active network interface found"

clean:
	./gradlew clean
