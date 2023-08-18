package me.fengming.maptranslate.nbt.tags;

import me.fengming.maptranslate.nbt.TagTypes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTag implements Tag {
    private final List<Tag> list;
    private final byte type;
    public static TagType<ListTag> TYPE = new TagType<ListTag>() {
        @Override
        public ListTag load(DataInput data) throws IOException {
            byte type = data.readByte();
            int length = data.readInt();
            TagType<?> tagType = TagTypes.getType(type);
            List<Tag> list = new ArrayList<>(length);

            for (int i = 0; i < length; i++) {
                list.add(tagType.load(data));
            }

            return new ListTag(list, type);
        }
    };

    public ListTag(List<Tag> list, byte type) {
        this.list = list;
        this.type = type;
    }

    
}
