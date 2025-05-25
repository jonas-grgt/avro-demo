package io.jonasg.avro.eventmothers;

import java.time.Instant;
import java.util.List;

import io.jonasg.BlogPostCreated;
import io.jonasg.PostProperties;
import io.jonasg.Status;

@SuppressWarnings("unused")
public class BlogPostCreatedMother {

    public static BlogPostCreated.Builder aBlogPostCreated() {
        return BlogPostCreated.newBuilder()
                .setId("1")
                .setTitle("A blog post")
                .setContent("This is a blog post")
                .setStatus(Status.PUBLISHED)
                .setTags(List.of("java", "avro"))
                .setTimestamp(Instant.parse("2023-10-01T10:00:00Z"))
                .setProperties(
                        PostProperties.newBuilder()
                                .setAuthor("Jonas")
                                .setCategory("Programming")
                                .setLanguage("English")
                                .build()
                );
    }
}
