package com.commit451.glyphith.service

import android.service.quicksettings.TileService
import com.commit451.glyphith.api.Glyph

class GlyphithTileService : TileService() {

    override fun onClick() {
        super.onClick()
        Glyph.blink()
    }
}
