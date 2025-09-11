import { Schema, model, models, Document, Types } from 'mongoose';

export interface IMenuItem {
  name: string;
  price?: number;
  description?: string;
  category?: string;
}

export interface IMenu extends Document {
  brandId: Types.ObjectId;
  date: Date;
  items: IMenuItem[];
  createdAt: Date;
  updatedAt: Date;
}

const menuItemSchema = new Schema<IMenuItem>({
  name: {
    type: String,
    required: true,
    trim: true,
  },
  price: {
    type: Number,
    min: 0,
  },
  description: {
    type: String,
    trim: true,
  },
  category: {
    type: String,
    trim: true,
  },
}, { _id: false });

const menuSchema = new Schema<IMenu>({
  brandId: {
    type: Schema.Types.ObjectId,
    ref: 'Brand',
    required: true,
  },
  date: {
    type: Date,
    required: true,
    default: Date.now,
  },
  items: [menuItemSchema],
}, {
  timestamps: true,
});

menuSchema.index({ brandId: 1, date: -1 });

export const Menu = models.Menu || model<IMenu>('Menu', menuSchema);