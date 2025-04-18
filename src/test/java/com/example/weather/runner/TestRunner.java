package com.example.weather.runner;

import com.example.weather.steps.CommonSteps;
import com.example.weather.steps.NegativeTestSteps;
import com.example.weather.steps.PositiveTestSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import java.util.Arrays;
import java.util.List;

public class TestRunner extends JUnitStories {

    public TestRunner() {
        super();
        configuredEmbedder().embedderControls()
                .doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true)
                .useThreads(2)
                .useStoryTimeoutInSecs(60);
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats()
                        .withFormats(Format.CONSOLE, Format.HTML, Format.XML)
                        .withRelativeDirectory("../build/jbehave"));
    }
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(),
                new CommonSteps(),
                new PositiveTestSteps(),
                new NegativeTestSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return Arrays.asList(
                "stories/positive/current_weather_stories.story",
                "stories/negative/api_error_stories.story"
        );
    }
}