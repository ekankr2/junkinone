import { Schema, model, models, Document } from 'mongoose';

export interface IBrand extends Document {
  name: string;
  website: string;
  createdAt: Date;
  updatedAt: Date;
}

const brandSchema = new Schema<IBrand>({
  name: {
    type: String,
    required: true,
    trim: true,
  },
  website: {
    type: String,
    required: true,
    trim: true,
  },
}, {
  timestamps: true,
});

export const Brand = models.Brand || model<IBrand>('Brand', brandSchema);