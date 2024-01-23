# Better-Farms

## Inspiration
A lot of the inspiration for this mod comes from multiple parts, a lot of credit goes to the old Harvest Festival mod, which this is very much based off of, while drawing a lot of inspiration from games such as Stardew Valley and Harvest Moon#
I wanted to create a mod that felt like Harvest Festival again while putting my own twist on it, and from those thoughts came.. whatever this is

## Configuration
Most of this mod can be configured with either changing values around in `config/betterfarms-common.json` or you can provide integration with this mod and other animal mods by adding onto the custom datapack in this mod!
For example, to add support for a cow, the file is constructed as such:
```json
{
  "daysPregnant": 9
}
```

This means the manager will now recognise the `minecraft:cow` as a valid target for the mod and because a value for `daysPregnant` has been passed, it will take 9 days for a birth to occur

To add support for a new mob that the mod does not provide default support for, you can create a json file in the following location: `data/{mod_name}/betterfarms/stats/{mob_id}.json`, where `{mod_name}` is the Mod ID of the mod and the `{mob_id}` is the registered ID for it (E.g. `minecraft:cow` for a cow)

The full list of valid values for the JSON are as follows, with their defaults:

- "daysPregnant" (Default = 0): How many days the specified animal will take to give birth. A value of 0 will give birth immediately
