# Demeter

## Inspiration
A lot of the inspiration for this mod comes from multiple parts, a lot of credit goes to the old Harvest Festival mod, which this is very much based off of, while drawing a lot of inspiration from games such as Stardew Valley and Harvest Moon!
I wanted to create a mod that felt like Harvest Festival again while putting my own twist on it, and from those thoughts came.. whatever this is

## Configuration
Most of this mod can be configured with either changing values around in `config/betterfarms-common.json` or you can provide integration with this mod and other animal mods by adding onto the custom datapack in this mod!
For example, to add support for a cow, the file is constructed as such:
```json
{
  "entity": "minecraft:cow",
  "daysPregnant": 9,
  "milking": {}
}
```

This means the manager will now recognise the `minecraft:cow` as a valid target for the mod and because a value for `daysPregnant` has been passed, it will take 9 days for a birth to occur, as well as accepting the animal as a valid milking target with the default input and output items
The `entity` parameter is present as a unique id to allow for all files to be unique and to alert the mod to any duplicate files. If there are too clashing files then the latter loaded one will not register

To add support for a new mob that the mod does not provide default support for, you can create a json file in the following location: `data/{mod_name}/betterfarms/stats/{mob_id}.json`, where `{mod_name}` is the Mod ID of the mod and the `{mob_id}` is the registered ID for it (E.g. `cow` for a cow)

The full list of valid values for the JSON are as follows, with their defaults:

- "daysPregnant" (Default = 0): How many days the specified animal will take to give birth. A value of 0 will give birth immediately
- "daysToGrowUp" (Default = 0): How many days the specified animal will take to grow up from being a child! If 0, it will instead revert to the Vanilla method of ticks
- "minChildrenPerBirth" (Default = 1): The min size a birth litter can be. Note: Should **never** be bigger than `maxChildrenPerBirth`
- "maxChildrenPerBirth" (Default = 1): The max size a birth litter can be. Note: Value should **never** be smaller than `minChildrenPerBirth`
- "milking" (Default = not applied, input = "minecraft:bucket", output = "minecraft:milk_bucket"): If the mod should add support for milking to the animal with custom support for input and output (NOT VALID: NEEDS CHANGING. New Section?)

### Milking
Milking is a smaller feature in this that is mainly to allow datapack and modpack makers to specify new animals that can be milked!
This is a parameter that takes a maximum of two arguments, `input` and `output`. 
Just defining `milking: {}` in your json (Like in the `minecraft:cow` file) allows for the milking to take place with the default input and output items
Adding a different input allows you to change what item an animal has to be interacted with be to be milked. (Default: `minecraft:bucket`) 
Adding a different output allows you to change what item you get from the milking process (Default: `minecraft:milk_bucket`)

An example of a different set of input and output items can be specified as shown below:

```json
{
  "milking": {
    "input": "minecraft:dirt",
    "output": "minecraft:diamond"
  }
}
```

In this example, whichever mob now had this specified, can now be milked by interacting with it with a dirt item, and it will return a diamond to the player!