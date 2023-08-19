package me.fengming.maptranslate.models.nbt.tags;

import me.fengming.maptranslate.models.nbt.TagTypes;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTag implements Tag {
    
    private List<Tag> list;
    private byte type;
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

    public List<Tag> getData() {
        return list;
    }
    public void setData(List<Tag> list) {
        this.list = list;
    }

    public ListTag(List<Tag> list, byte type) {
        this.list = list;
        this.type = type;
    }

}
