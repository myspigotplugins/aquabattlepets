package io.github.shiryu.aquabattlepets.util.version;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class VersionMatched<T> {

    /**
     * Version of the server, pattern must be like that;
     * 1_14_R1
     * 1_13_R2
     */
    @NotNull
    private final String rawVersion;

    /**
     * Classes that match.
     */
    @NotNull
    private final List<VersionClass<T>> versionClasses;

    /**
     * @param rawVersion Raw server version text
     * (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final String rawVersion, @NotNull final List<VersionClass<T>> versionClasses) {
        this.rawVersion = rawVersion;
        this.versionClasses = versionClasses;
    }

    /**
     * @param version Server version
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final Version version, @NotNull final List<VersionClass<T>> versionClasses) {
        this(version.raw(), versionClasses);
    }

    /**
     * @param rawVersion Raw server version text
     * (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final String rawVersion, @NotNull final Class<? extends T>... versionClasses) {
        this(
            rawVersion,
            Arrays.stream(versionClasses)
                .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
                .collect(Collectors.toList()));
    }

    /**
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final Class<? extends T>... versionClasses) {
        this(
            new Version(),
            Arrays.stream(versionClasses)
                .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
                .collect(Collectors.toList()));
    }

    /**
     * Matches classes
     *
     * @return class that match or throw exception
     */
    @NotNull
    private Class<? extends T> match() {
        return this.versionClasses.stream()
            .filter(versionClass -> versionClass.match(this.rawVersion))
            .map(VersionClass::getVersionClass)
            .findFirst()
            .orElseThrow(() ->
                new IllegalStateException("match() -> Couldn't find any matched class on " +
                    '"' + this.rawVersion + '"' + " version!"));
    }

}