package tiny.angry.kitten.homeapplication.invocation.media

enum class MediaFunction{
    Next,
    Previous,
    Pause,
    Play,
    VolumeDown,
    VolumeUp,
    PowerOff;

    override fun toString(): String = name
}