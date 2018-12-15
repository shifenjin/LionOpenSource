package com.modulelifecycle.plugin;


import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ModuleLifeCyclePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("Module lifecycle plugin worked");

        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new ModuleLifeCycleTransform(project));
    }
}
