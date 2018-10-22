<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="viewModel"
            type="${packageName}.${funtionName}.viewmodel.${Name}ViewModel"/>
    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="${packageName}.${funtionName}.${activityName}">

        <#if hasProgress>
        <include
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            layout="@layout/loading_state"
            app:iProgress="@{viewModel}"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent" />
        </#if>
    </android.support.constraint.ConstraintLayout>

</layout>