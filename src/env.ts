const env = {
    mode: process.env.NODE_ENV,
    BASE_URL:
        process.env.NODE_ENV === 'production'
            ? 'https://junkinone.com'
            : 'http://localhost:3000',
    CDN_URL: 'https://cdn.junkinone.com',
    DATABASE_URL: process.env.DATABASE_URL,
    MONGODB_URI: process.env.MONGODB_URI,
};

export default env;
