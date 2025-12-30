package com.affirmation.app.presentation.screen.categories.helper

import com.affirmation.app.presentation.screen.categories.model.MusicCategoryModel


object Category {
    val categoryList = listOf(
        MusicCategoryModel(
            "sleep",
            "Sleep & Dream",
            "Relax and drift away",
            "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766805789/pexels-brett-sayles-3910141_2_gqicrd.jpg",
            "https://res.cloudinary.com/dkbbgpfcl/video/upload/v1766965204/dream-143281_m85qp1.mp3"
        ),
        MusicCategoryModel(
            "morning",
            "Morning Energy",
            "Start with positivity",
            "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766803506/yana-gorbunova-r3Kpb5X7Ep8-unsplash_fyyrkl.jpg",
            "https://res.cloudinary.com/dkbbgpfcl/video/upload/v1766965846/morning-coffee_medium-333080_aen5tg.mp3"
        ),
        MusicCategoryModel(
            "focus",
            "Focus & Power",
            "Push your limits",
            "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766804917/pexels-njeromin-15364788_lfkzi5.jpg",
            "https://res.cloudinary.com/dkbbgpfcl/video/upload/v1766965203/jazzy-focus-1-lofi-jazz-371178_xsunc2.mp3"
        ),
        MusicCategoryModel(
            "nature",
            "Nature Sounds",
            "Connect with earth",
            "https://res.cloudinary.com/dkbbgpfcl/image/upload/v1766853041/fire_new_xaelw7.jpg",
            "https://res.cloudinary.com/dkbbgpfcl/video/upload/v1766965202/water-fountain-healing-music-239455_c8ofmk.mp3"
        )
    )
}