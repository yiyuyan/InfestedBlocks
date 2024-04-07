package cn.ksmcbrigade.ibs;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod("ibs")
public class InfestedBlocks {

    public static boolean init = false;
    public static ArrayList<String> whiteList = new ArrayList<>();

    public InfestedBlocks() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        if(!init){
            init();
        }
    }

    public static void init() throws IOException{
        File config = new File("config/ib-config.json");
        if(!config.exists()){
            Files.writeString(config.toPath(),new JsonArray().toString());
        }
        JsonParser.parseString(Files.readString(config.toPath())).getAsJsonArray().forEach(k -> whiteList.add(k.getAsString()));
        init = true;
    }

    public static boolean con(ResourceLocation rel){
        AtomicBoolean ret = new AtomicBoolean(false);
        whiteList.forEach(k -> {
            String[] np = k.split(":");
            if(rel.getNamespace().equalsIgnoreCase(np[0]) && rel.getPath().equalsIgnoreCase(np[1])){
                ret.set(true);
            }
        });
        return ret.get();
    }

    public static void infestedBlocks$spawnInfestation(ServerLevel p_54181_, BlockPos p_54182_) {
        Silverfish silverfish = EntityType.SILVERFISH.create(p_54181_);
        if (silverfish != null) {
            silverfish.moveTo((double)p_54182_.getX() + 0.5D, p_54182_.getY(), (double)p_54182_.getZ() + 0.5D, 0.0F, 0.0F);
            p_54181_.addFreshEntity(silverfish);
            silverfish.spawnAnim();
        }
    }
}
