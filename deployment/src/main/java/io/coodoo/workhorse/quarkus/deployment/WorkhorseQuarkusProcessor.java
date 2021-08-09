package io.coodoo.workhorse.quarkus.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.processor.DotNames;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;

class WorkhorseQuarkusProcessor {

    private static final String FEATURE = "workhorse-quarkus";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    IndexDependencyBuildItem indexExternalDependency() {
        return new IndexDependencyBuildItem("io.coodoo", "workhorse");
    }

    @BuildStep
    public void declareWorkhorseAsBean(CombinedIndexBuildItem index, BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        List<String> workhorseBeans = index.getIndex().getKnownClasses().stream().filter(ci -> !Modifier.isAbstract(ci.flags())).map(ci -> ci.name().toString())
                        .filter(c -> c.startsWith("io.coodoo.workhorse.")).collect(Collectors.toList());

        additionalBeans.produce(
                        new AdditionalBeanBuildItem.Builder().addBeanClasses(workhorseBeans).setUnremovable().setDefaultScope(DotNames.DEFAULT_BEAN).build());
    }

}
