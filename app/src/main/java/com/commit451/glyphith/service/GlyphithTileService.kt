package com.commit451.glyphith.service

import android.service.quicksettings.TileService
import com.commit451.glyphith.api.Glyph
import com.commit451.glyphith.data.PatternLoader

class GlyphithTileService : TileService() {

    override fun onClick() {
        super.onClick()
        val patterns = PatternLoader.loadPatterns(resources)
        Glyph.setPattern(patterns.first())
        Glyph.animate()
    }
}
