## v2.2.1
- Added support for Farmer's Delight recipes (and other custom mod recipes that output multiple items at the same time)
- Fixed the mod breaking all cutting board recipes

## v2.2.0
- Performance improved on item entity banning
- Fixed and improved recipe banning; now it should no longer break due to a mod incompatibility, and its performance was improved too.

### Credits:
- someaddon for suggesting me where to mixin to disable recipes on datapack reload

## v2.0.0

- The config is now JSON5 based, a new file will be created due to the changes.
- Added EMI Support by acikek (#14)
- Added comments to configs
- Added optimization configs
- Added regex on entries that start with !
- Added NBT based item blacklisting
- Removed $ banning methods (Redundant due to regex)
- The "This item is disabled" message is now translatable
- Items will now disappear from loot containers when generated