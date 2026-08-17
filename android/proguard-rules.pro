# BoardDataAdapter writes fully-qualified class names into the wire JSON and resolves them
# via Class.forName, and Gson deserializes every DTO via field reflection — R8 must not
# rename/strip anything under the shared protocol package or the client will fail to
# deserialize messages from the (unmodified) desktop server at runtime.
-keep class it.polimi.ingsw.communication.** { *; }
