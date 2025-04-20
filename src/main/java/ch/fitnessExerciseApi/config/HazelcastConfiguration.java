package ch.fitnessExerciseApi.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        // Enable the Jet engine
        config.getJetConfig().setEnabled(true);
        config.addMapConfig(new MapConfig()
                .setName("default")
                .setTimeToLiveSeconds(3600)); // Entries expire after 1 hour
        return config;
    }
}
